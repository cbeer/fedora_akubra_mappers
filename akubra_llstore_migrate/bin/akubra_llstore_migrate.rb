#!/usr/bin/env jruby
require 'rubygems'
require 'bundler'
Bundler.require(:default)

require 'slop'

$LOAD_PATH.unshift(File.expand_path('../lib', File.dirname(__FILE__)))
require 'akubra'

opts = Slop.parse do
  banner "Usage: akubra_llstore_migrate.rb [options]"
  on :h, :help, :tail => true do
    puts help
    exit
  end

  on :f, :from=, 'current akubra_llstore configuration', :optional => false
  on :t, :to=, ' akubra_llstore configuration to migrate to', :optional => false
  on :o, :objectStore, :default => true 
  on :d, :datastreamStore, :default => true 
end

@migration = Akubra::Migration.new(:from => File.expand_path(opts[:from]), :to => File.expand_path(opts[:to]))
@migration.migrate_object_store! if opts[:objectStore]
@migration.migrate_datastream_store! if opts[:datastreamStore]
