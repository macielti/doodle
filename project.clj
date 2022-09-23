(defproject doodle "0.1.0-SNAPSHOT"
  :description "A microservice for notification (email)"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins [[lein-cloverage "1.2.3"]
            [lein-environ "1.2.0"]]

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [danlentz/clj-uuid "0.1.9"]
                 [net.clojars.macielti/common-clj "17.22.19"]
                 [ch.qos.logback/logback-classic "1.4.1"]
                 [org.apache.kafka/kafka-clients "2.8.0"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [com.stuartsierra/component "1.1.0"]
                 [camel-snake-kebab "0.4.3"]
                 [prismatic/schema "1.4.0"]
                 [clj-http-fake "1.0.3"]
                 [clj-sendgrid "0.1.2"]
                 [cheshire "5.11.0"]
                 [environ "1.2.0"]]

  :profiles {:test {:env {:clj-env         "test"
                          :clj-config-path "resources/config.example.json"}}}

  :resource-paths ["resources"]

  :repl-options {:init-ns doodle.components}

  :test-paths ["test/unit" "test/integration" "test/helpers"]

  :main doodle.components/start-system!)
