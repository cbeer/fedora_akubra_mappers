module Akubra
  class Store
    attr_reader :bean

    def initialize bean
      @bean = bean
    end

    def each_file &block
      return to_enum(:each_file).to_a unless block_given?
      store.listBlobIds(nil).each { |x| block.call(File.join(baseDir, x.schemeSpecificPart)) }
    end

    def each &block
      return to_enum(:each).to_a unless block_given?
      bean.open_connection(nil, nil).listBlobIds(nil).each { |x| block.call(x.schemeSpecificPart) }
    end

    def store
      @store ||= begin
                   fl = bean.java_class.declared_field('store')
      fl.accessible = true
      fl.value(bean).open_connection(nil, nil)
                 end
    end

    def baseDir
      @baseDir ||= begin
      fl = store.java_class.declared_field('baseDir')
      fl.accessible = true
      fl.value(store).to_s
                   end
    end

    def mapper
      @mapper ||= begin
      fl = bean.java_class.declared_field('mapper')
      fl.accessible = true
      fl.value(bean)
                  end
    end

  end
end
