(ns microservice-notification.wire.in.notification
  (:require [schema.core :as s]))

(s/defschema NotificationMessage
  {:topic (s/enum :notification)
   :value {:email   s/Str
           :title   s/Str
           :content s/Str}})
