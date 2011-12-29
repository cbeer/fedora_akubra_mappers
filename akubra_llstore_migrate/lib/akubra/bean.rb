require 'nokogiri'
module Akubra
  class Bean

    def initialize file
      @file = file
    end

    def objectStore
      ref = xml.xpath('//bean[@class="org.fcrepo.server.storage.lowlevel.akubra.AkubraLowlevelStorage"]/constructor-arg[1]/ref/@bean').to_s

      store = bean(ref)
    end

    def datastreamStore
      ref = xml.xpath('//bean[@class="org.fcrepo.server.storage.lowlevel.akubra.AkubraLowlevelStorage"]/constructor-arg[2]/ref/@bean').to_s

      store = bean(ref)
    end


    protected
    def xml
      @xml ||= Nokogiri::XML(File.read(@file))
    end

    def beans 
      @beans ||= org.springframework.context.support.ClassPathXmlApplicationContext.new("file:#{File.expand_path(@file)}")
    end

    def bean ref
      beans.getBean(ref)
    end
  end
end
