(ns integration.notification
  (:require [clojure.test :refer :all]
            [schema.test :as s]
            [cheshire.core :as json]
            [com.stuartsierra.component :as component]
            [clj-http.fake :refer [with-global-fake-routes]]
            [doodle.components :as components])
  (:import (org.apache.kafka.clients.consumer ConsumerRecord)
           (java.util UUID)))

(s/deftest notification-test
  (with-global-fake-routes
    {"https://api.sendgrid.com/v3/mail/send" (constantly {})}
    (let [{{:keys [consumer]} :consumer :as system} (component/start components/system-test)]

      (.addRecord (:kafka-client consumer) (ConsumerRecord. "notification" 0 (long 0) nil (json/encode {:email             "brunodonascimentomaciel@gmail.com"
                                                                                                        :password-reset-id (str (UUID/randomUUID))
                                                                                                        :title             "Password Reset"
                                                                                                        :content           "Link to reset password"})))
      (component/stop system))))
