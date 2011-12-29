Gem::Specification.new do |s|
  s.name = "akubra_llstore_migrate"
  s.version = "0.0.1"
  s.platform = Gem::Platform::RUBY
  s.authors = ["Chris Beer"]
  s.email = ["chris@cbeer.info"]
  s.summary = %q{Fedora Commons Akubra Low-level storage migration utility }
  s.description = %q{REQUIRES FCREPO 3.4+}
  s.homepage = "http://github.com/cbeer/fedora_akubra_mappers/tree/master/akubra_llstore_migrate"

  s.files        = Dir['lib/**/*'] + Dir['bin/**/*']
  s.executables  = ["akubra_llstore_migrate.rb"]
  s.require_paths = ["lib"]
  s.platform     = 'java'

  s.add_dependency "nokogiri"
  s.add_dependency "slop"

  s.add_development_dependency("bundler", ">= 1.0.14")
  s.add_development_dependency("rspec")
  s.add_development_dependency("jruby-openssl")
end
