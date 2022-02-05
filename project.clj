(defproject doodle "0.1.0-SNAPSHOT"
  :description "A microservice for notification (email)"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins [[lein-cloverage "1.2.2"]
            [lein-environ "1.2.0"]]

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [net.clojars.macielti/common-clj "9.13.9"]
                 [ch.qos.logback/logback-classic "1.2.10"]
                 [org.apache.kafka/kafka-clients "2.8.0"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [com.stuartsierra/component "1.0.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [camel-snake-kebab "0.4.2"]
                 [prismatic/schema "1.2.0"]
                 [clj-http-fake "1.0.3"]
                 [clj-sendgrid "0.1.2"]
                 [cheshire "5.10.2"]
                 [environ "1.2.0"]]

  :profiles {:test {:env {:clj-env         "test"
                          :clj-config-path "resources/config.example.json"}}}

  :resource-paths ["resources"]

  :repl-options {:init-ns doodle.components}

  :test-paths ["test/unit" "test/integration" "test/helpers"]

  :main doodle.components/start-system!)
