(ns degree9.ui.notification
  (:require [uikit-hl.notification :as notify]))

(def ^:private defaults {:timeout "2500"})

(def notify! notify/notification)

(defn primary! [opts]
  (notify! (merge defaults opts {:status "primary"})))

(defn success! [opts]
  (notify! (merge defaults opts {:status "success"})))

(defn warning! [opts]
  (notify! (merge defaults opts {:status "warning"})))

(defn danger! [opts]
  (notify! (merge defaults opts {:status "danger"})))
