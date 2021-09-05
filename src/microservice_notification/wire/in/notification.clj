(ns microservice-notification.wire.in.notification
  (:require [schema.core :as s]))

;TODO: Try to use a loose schema here
(s/defschema NotificationMessage
  {:topic (s/enum :notification)
   :value {:email             s/Str
           :password-reset-id s/Str
           :title             s/Str
           :content           s/Str}})
