(ns integration.notification
  (:use [clojure pprint])
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [microservice-notification.components :as components]
            [schema.test :as s]
            [cheshire.core :as json])
  (:import (org.apache.kafka.clients.consumer ConsumerRecord)))

(use-fixtures :once s/validate-schemas)

(s/deftest auth-test
  (let [{{:keys [consumer]} :consumer :as system} (components/start-system!)]

    (.addRecord consumer (ConsumerRecord. "notification" 0 (long 2) nil (json/encode {:email   "brunodonascimentomaciel@gmail.com"
                                                                                      :title   "Password Reset"
                                                                                      :content "Link to reset password"})))
    (component/stop system)))
