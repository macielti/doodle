(ns integration.notification
  (:use [clojure pprint])
  (:require [clojure.test :refer :all]
            [clj-http.fake :refer [with-global-fake-routes]]
            [com.stuartsierra.component :as component]
            [microservice-notification.components :as components]
            [schema.test :as s]
            [cheshire.core :as json])
  (:import (org.apache.kafka.clients.consumer ConsumerRecord)))

(use-fixtures :once s/validate-schemas)

(s/deftest notification-test
  (with-global-fake-routes
    {"https://api.sendgrid.com/v3/mail/send" (constantly {})}
    (let [{{:keys [consumer]} :consumer :as system} (components/start-system!)]

      (.addRecord (:kafka-client consumer) (ConsumerRecord. "notification" 0 (long 0) nil (json/encode {:email   "brunodonascimentomaciel@gmail.com"
                                                                                                        :title   "Password Reset"
                                                                                                        :content "Link to reset password"})))
      (component/stop system))))
