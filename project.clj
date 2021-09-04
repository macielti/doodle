(defproject microservice-notification "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.stuartsierra/component "1.0.0"]
                 [org.apache.kafka/kafka-clients "2.1.0"]
                 [io.pedestal/pedestal.service "0.5.7"]
                 [spyscope "0.1.6"]
                 [camel-snake-kebab "0.4.2"]
                 [org.clojure/tools.logging "1.1.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [cheshire "5.10.0"]
                 [prismatic/schema "1.1.11"]]

  :injections [(require 'spyscope.core)]

  :resource-paths ["resources"]

  :repl-options {:init-ns microservice-notification.components}

  :main microservice-notification.components/start-system!)
