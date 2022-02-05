(ns integration.notification
  (:require [clojure.test :refer :all]
            [clj-uuid]
            [schema.test :as s]
            [com.stuartsierra.component :as component]
            [matcher-combinators.test :refer [match?]]
            [clj-http.fake :refer [with-global-fake-routes]]
            [common-clj.component.helper.core :as component.helper]
            [common-clj.component.kafka.producer :as component.kafka.producer]
            [common-clj.component.kafka.consumer :as component.kafka.consumer]
            [doodle.components :as components])
  (:import (java.util UUID)))

(s/deftest notification-test
  (with-global-fake-routes
    {"https://api.sendgrid.com/v3/mail/send" (constantly {})}
    (let [{{:keys [consumer]} :consumer :as system} (component/start components/system-test)
          producer (component.helper/get-component-content :producer system)
          consumer (component.helper/get-component-content :consumer system)]

      (component.kafka.producer/produce! {:topic   :notification
                                          :message {:email             "brunodonascimentomaciel@gmail.com"
                                                    :password-reset-id (str (UUID/randomUUID))
                                                    :title             "Password Reset"
                                                    :content           "Link to reset password"}}
                                         producer)

      (Thread/sleep 5000)

      (testing "that the message was consumed with success"
        (is (match? [{:message {:content           "Link to reset password"
                                :email             "brunodonascimentomaciel@gmail.com"
                                :password-reset-id clj-uuid/uuid-string?
                                :title             "Password Reset"}
                      :topic   :notification}]
                    (component.kafka.consumer/consumed-messages consumer))))

      (component/stop system))))
