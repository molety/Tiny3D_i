######## iアプリ ビルド用Rakefile

require 'rake/clean'


### ↓↓↓環境に合わせて書き換える↓↓↓

# DoJaのターゲットプロファイル
DOJA_PROFILE = "DoJa-4.0"
# iアプリ開発ツールのインストール先
DOJA_DIR = "C:/iDKDoJa5.1"
# ProGuardのパス
PROGUARD_PATH = "C:/WTK2.5.1/bin/proguard.jar"
# 7-Zipのパス
SEVENZIP_PATH = "\"C:/Program Files/7-Zip/7z.exe\""

# コンパイル対象のソース
source_files = FileList["src/*.java"]
source_files.exclude("**/Test*.java")  # 除外したいファイルがあればここへ
# jarに含めるリソース
res_files = FileList["res/*"]

# jam、jarを生成するディレクトリ
PACKAGE_DIR = "pkg"  # 念のためbinを上書きしないようにしていますが、binを指定しても動きます。

### ↑↑↑環境に合わせて書き換える↑↑↑


DOJA_LIB_DIR = "#{DOJA_DIR}/lib/profile/#{DOJA_PROFILE}"
DOJA_CLASSPATH = "#{DOJA_LIB_DIR}/classes.zip;#{DOJA_LIB_DIR}/doja_classes.zip"
PREVERIFY_PATH = "#{DOJA_DIR}/bin/preverify.exe"

PREPROCESSED_DIR = "preprocessed"
COMPILED_DIR = "compiled"
OBFUSCATED_DIR = "obfuscated"
PREVERIFIED_DIR = "preverified"
PACKAGE_TMP_DIR = "pkg_tmp"

orig_jam = nil
jam_info = nil
new_jam_path = nil
new_jar_path = nil

CLEAN.include(PREPROCESSED_DIR, COMPILED_DIR, OBFUSCATED_DIR, PREVERIFIED_DIR, PACKAGE_TMP_DIR)
if PACKAGE_DIR != "bin"  # binディレクトリは削除対象にしない
  CLOBBER.include(PACKAGE_DIR)
end

directory PREPROCESSED_DIR
directory COMPILED_DIR
directory OBFUSCATED_DIR
directory PREVERIFIED_DIR
directory PACKAGE_TMP_DIR
directory PACKAGE_DIR

desc "準備 (内部用)"
task :prepare => [] do
  # オリジナルのjamファイルを読み込む
  # 最初に見つかったjamファイルを採用。普通は1個なので大丈夫なはず。
  orig_jam_path = FileList["bin/*.jam"][0]
  if orig_jam_path.nil?
    puts "jam file not found!"
    exit
  end
  orig_jam = IO.readlines(orig_jam_path)

  # jamファイルからHashを作る
  jam_info = Hash.new
  orig_jam.each do |line|
    /^(\S+) = (.*)$/ =~ line
    jam_info[Regexp.last_match(1)] = Regexp.last_match(2)
  end

  # これから作るjamとjarのパス
  new_jam_path = orig_jam_path.gsub(/bin/, PACKAGE_DIR)
  new_jar_path = new_jam_path.ext("jar")
end

desc "プリプロセス"
task :preprocess => [:prepare, PREPROCESSED_DIR] do
  rm Dir.glob("#{PREPROCESSED_DIR}/*")
  source_files.each do |path|
    source = IO.readlines(path)
    prepro_source = Array.new
    skip = false
### ↓↓↓環境に合わせて書き換える↓↓↓
    # この辺りで好きなようにソースを加工できます。
    source.each do |line|
      # //#ifdef DEBUG ～ //#endif で囲まれた行をコメントアウトする
      if /^\s*\/\/\s*#ifdef\s+DEBUG/ =~ line
        skip = true
      elsif /^\s*\/\/\s*#endif/ =~ line
        skip = false
      else
        line.gsub!(/^/, "//") if skip
      end
        prepro_source.push(line)
    end
### ↑↑↑環境に合わせて書き換える↑↑↑
    prepro_path = path.gsub(/^src/, PREPROCESSED_DIR)
    File.open(prepro_path, "w") do |file|
      file.write(prepro_source)
    end
  end
end

desc "コンパイル"
task :compile => [:preprocess, COMPILED_DIR] do
  rm Dir.glob("#{COMPILED_DIR}/*")
  sh "javac -bootclasspath #{DOJA_CLASSPATH} -source 1.4 -target 1.4 -g:none -d #{COMPILED_DIR} #{PREPROCESSED_DIR}/*.java"
end

desc "難読化／最適化"
task :obfuscate => [:prepare, :compile, OBFUSCATED_DIR] do
  rm Dir.glob("#{OBFUSCATED_DIR}/*")
  sh "java -jar #{PROGUARD_PATH} -injars #{COMPILED_DIR} -outjars #{OBFUSCATED_DIR} -libraryjars #{DOJA_CLASSPATH} -keep public class #{jam_info['AppClass']}"
end

desc "事前検証"
task :preverify => [:obfuscate, PREVERIFIED_DIR] do
  rm Dir.glob("#{PREVERIFIED_DIR}/*")
  sh "#{PREVERIFY_PATH} -classpath #{DOJA_CLASSPATH} -d #{PREVERIFIED_DIR} #{OBFUSCATED_DIR}"
end

desc "jar作成"
task :jar => [:preverify, PACKAGE_TMP_DIR, PACKAGE_DIR] do
  rm Dir.glob("#{PACKAGE_TMP_DIR}/*")
  rm_f new_jar_path
  cp FileList["#{PREVERIFIED_DIR}/*"], PACKAGE_TMP_DIR, {:preserve => true}
  cp res_files, PACKAGE_TMP_DIR, {:preserve => true}
  cd PACKAGE_TMP_DIR do
    sh "#{SEVENZIP_PATH} a -tzip -mx=9 -mfb=128 ../#{new_jar_path} *"
  end
end

desc "jam作成"
task :jam => [:jar, PACKAGE_DIR] do
  # 現在時刻とjarファイルサイズを得る
  curr_time = Time.now.strftime("%a, %d %b %Y %H:%M:%S")
  jar_size = File.size(new_jar_path)

  # 新しくjamファイルを作る
  new_jam = Array.new
  orig_jam.each do |line|
    new_line = line.dup
    new_line.gsub!(/(LastModified = ).*$/, "\\1#{curr_time}")
    new_line.gsub!(/(AppSize = ).*$/, "\\1#{jar_size}")
    new_jam.push(new_line)
  end

  # jamファイルを書き出す
  File.open(new_jam_path, "w") do |file|
    file.write(new_jam)
  end
end

task :default => [:jam, :jar]
