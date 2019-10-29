######## i�A�v�� �r���h�pRakefile

require 'rake/clean'


### ���������ɍ��킹�ď��������遫����

# DoJa�̃^�[�Q�b�g�v���t�@�C��
DOJA_PROFILE = "DoJa-4.0"
# i�A�v���J���c�[���̃C���X�g�[����
DOJA_DIR = "C:/iDKDoJa5.1"
# ProGuard�̃p�X
PROGUARD_PATH = "C:/WTK2.5.1/bin/proguard.jar"
# 7-Zip�̃p�X
SEVENZIP_PATH = "\"C:/Program Files/7-Zip/7z.exe\""

# �R���p�C���Ώۂ̃\�[�X
source_files = FileList["src/*.java"]
source_files.exclude("**/Test*.java")  # ���O�������t�@�C��������΂�����
# jar�Ɋ܂߂郊�\�[�X
res_files = FileList["res/*"]

# jam�Ajar�𐶐�����f�B���N�g��
PACKAGE_DIR = "pkg"  # �O�̂���bin���㏑�����Ȃ��悤�ɂ��Ă��܂����Abin���w�肵�Ă������܂��B

### ���������ɍ��킹�ď��������遪����


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
if PACKAGE_DIR != "bin"  # bin�f�B���N�g���͍폜�Ώۂɂ��Ȃ�
  CLOBBER.include(PACKAGE_DIR)
end

directory PREPROCESSED_DIR
directory COMPILED_DIR
directory OBFUSCATED_DIR
directory PREVERIFIED_DIR
directory PACKAGE_TMP_DIR
directory PACKAGE_DIR

desc "���� (�����p)"
task :prepare => [] do
  # �I���W�i����jam�t�@�C����ǂݍ���
  # �ŏ��Ɍ�������jam�t�@�C�����̗p�B���ʂ�1�Ȃ̂ő��v�Ȃ͂��B
  orig_jam_path = FileList["bin/*.jam"][0]
  if orig_jam_path.nil?
    puts "jam file not found!"
    exit
  end
  orig_jam = IO.readlines(orig_jam_path)

  # jam�t�@�C������Hash�����
  jam_info = Hash.new
  orig_jam.each do |line|
    /^(\S+) = (.*)$/ =~ line
    jam_info[Regexp.last_match(1)] = Regexp.last_match(2)
  end

  # ���ꂩ����jam��jar�̃p�X
  new_jam_path = orig_jam_path.gsub(/bin/, PACKAGE_DIR)
  new_jar_path = new_jam_path.ext("jar")
end

desc "�v���v���Z�X"
task :preprocess => [:prepare, PREPROCESSED_DIR] do
  rm Dir.glob("#{PREPROCESSED_DIR}/*")
  source_files.each do |path|
    source = IO.readlines(path)
    prepro_source = Array.new
    skip = false
### ���������ɍ��킹�ď��������遫����
    # ���̕ӂ�ōD���Ȃ悤�Ƀ\�[�X�����H�ł��܂��B
    source.each do |line|
      # //#ifdef DEBUG �` //#endif �ň͂܂ꂽ�s���R�����g�A�E�g����
      if /^\s*\/\/\s*#ifdef\s+DEBUG/ =~ line
        skip = true
      elsif /^\s*\/\/\s*#endif/ =~ line
        skip = false
      else
        line.gsub!(/^/, "//") if skip
      end
        prepro_source.push(line)
    end
### ���������ɍ��킹�ď��������遪����
    prepro_path = path.gsub(/^src/, PREPROCESSED_DIR)
    File.open(prepro_path, "w") do |file|
      file.write(prepro_source)
    end
  end
end

desc "�R���p�C��"
task :compile => [:preprocess, COMPILED_DIR] do
  rm Dir.glob("#{COMPILED_DIR}/*")
  sh "javac -bootclasspath #{DOJA_CLASSPATH} -source 1.4 -target 1.4 -g:none -d #{COMPILED_DIR} #{PREPROCESSED_DIR}/*.java"
end

desc "��ǉ��^�œK��"
task :obfuscate => [:prepare, :compile, OBFUSCATED_DIR] do
  rm Dir.glob("#{OBFUSCATED_DIR}/*")
  sh "java -jar #{PROGUARD_PATH} -injars #{COMPILED_DIR} -outjars #{OBFUSCATED_DIR} -libraryjars #{DOJA_CLASSPATH} -keep public class #{jam_info['AppClass']}"
end

desc "���O����"
task :preverify => [:obfuscate, PREVERIFIED_DIR] do
  rm Dir.glob("#{PREVERIFIED_DIR}/*")
  sh "#{PREVERIFY_PATH} -classpath #{DOJA_CLASSPATH} -d #{PREVERIFIED_DIR} #{OBFUSCATED_DIR}"
end

desc "jar�쐬"
task :jar => [:preverify, PACKAGE_TMP_DIR, PACKAGE_DIR] do
  rm Dir.glob("#{PACKAGE_TMP_DIR}/*")
  rm_f new_jar_path
  cp FileList["#{PREVERIFIED_DIR}/*"], PACKAGE_TMP_DIR, {:preserve => true}
  cp res_files, PACKAGE_TMP_DIR, {:preserve => true}
  cd PACKAGE_TMP_DIR do
    sh "#{SEVENZIP_PATH} a -tzip -mx=9 -mfb=128 ../#{new_jar_path} *"
  end
end

desc "jam�쐬"
task :jam => [:jar, PACKAGE_DIR] do
  # ���ݎ�����jar�t�@�C���T�C�Y�𓾂�
  curr_time = Time.now.strftime("%a, %d %b %Y %H:%M:%S")
  jar_size = File.size(new_jar_path)

  # �V����jam�t�@�C�������
  new_jam = Array.new
  orig_jam.each do |line|
    new_line = line.dup
    new_line.gsub!(/(LastModified = ).*$/, "\\1#{curr_time}")
    new_line.gsub!(/(AppSize = ).*$/, "\\1#{jar_size}")
    new_jam.push(new_line)
  end

  # jam�t�@�C���������o��
  File.open(new_jam_path, "w") do |file|
    file.write(new_jam)
  end
end

task :default => [:jam, :jar]
