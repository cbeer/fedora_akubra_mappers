require 'spec_helper'
require 'fileutils'

describe 'Akubra Llstore Migrations' do
  before(:each) do
    Dir.chdir(File.dirname(__FILE__)) do
    FileUtils.rm_rf(File.expand_path('data-dest'))
    FileUtils.mkdir(File.expand_path('data-dest'))

    @opts = { :catalina_home => File.dirname(__FILE__),
             :from => File.expand_path('akubra-llstore.xml.source'),
             :to => File.expand_path('akubra-llstore.xml.dest')
           }
    end
  end

  it "should migrate object stores" do
    Dir.chdir(@opts[:catalina_home]) do
      @migration = Akubra::Migration.new(@opts)
      @migration.migrate_object_store!

      dest_files = Dir.glob(File.join(File.expand_path('data-dest'), '**/*')).to_a
      dest_files.should include("#{File.dirname(__FILE__)}/data-dest/pairtree_root/in/fo/+f/ed/or/a=/fe/do/ra/-s/ys/te/m+/Co/nt/en/tM/od/el/-3/,0/info+fedora=fedora-system+ContentModel-3,0/object.xml",
                                "#{File.dirname(__FILE__)}/data-dest/pairtree_root/in/fo/+f/ed/or/a=/fe/do/ra/-s/ys/te/m+/Fe/do/ra/Ob/je/ct/-3/,0/info+fedora=fedora-system+FedoraObject-3,0/object.xml",
                                "#{File.dirname(__FILE__)}/data-dest/pairtree_root/in/fo/+f/ed/or/a=/fe/do/ra/-s/ys/te/m+/Se/rv/ic/eD/ef/in/it/io/n-/3,/0/info+fedora=fedora-system+ServiceDefinition-3,0/object.xml",
                                "#{File.dirname(__FILE__)}/data-dest/pairtree_root/in/fo/+f/ed/or/a=/fe/do/ra/-s/ys/te/m+/Se/rv/ic/eD/ep/lo/ym/en/t-/3,/0/info+fedora=fedora-system+ServiceDeployment-3,0/object.xml"
                               )
    end
  end
end
