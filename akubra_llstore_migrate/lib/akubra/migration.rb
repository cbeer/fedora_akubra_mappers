require 'java'
require 'fileutils'
Dir.glob(File.expand_path('../../jars', File.dirname(__FILE__)) + "/*.jar").map { |x| require x }

module Akubra
class Migration

  def initialize options
    @options = options
  end

  def migrate_object_store!
    migrate!(:objectStore)
  end

  def migrate_datastream_store!
    migrate!(:datastreamStore)
  end

  protected
  def migrate! storeName
    source[storeName].each_file do |f|
      pid = source[storeName].mapper.getExternalId(java.net.URI.create(f)).to_s
      dest = File.join(destination[storeName].baseDir, destination[storeName].mapper.getInternalId(java.net.URI.create(pid)).rawSchemeSpecificPart)
      FileUtils.mkdir_p(File.dirname(dest))
      FileUtils.cp(f, dest)
    end
  end

  def source 
    @source ||= begin
                  h = {}
                  bean = Bean.new(@options[:from])
                  h[:objectStore] = Store.new(bean.objectStore)
                  h[:datastreamStore] = Store.new(bean.datastreamStore)
                  h
                end
  end

  def destination
    @destination ||= begin
                  h = {}
                  bean = Bean.new(@options[:to])
                  h[:objectStore] = Store.new(bean.objectStore)
                  h[:datastreamStore] = Store.new(bean.datastreamStore)
                  h
                end
  end
end

end
