(ns degree9.ui
  (:require-macros degree9.ui)
  (:require [hoplon.core :as h]))

(defn stylesheet [attr]
  (h/link :rel "stylesheet" attr))

(defn script [attr]
  (h/script :type "text/javascript" :async true :defer true attr))

(defn map-scripts [scripts]
  (map script scripts))

(defn map-stylesheets [styles]
  (map stylesheet styles))

(h/defelem application [{:keys [styles scripts] :as attr} kids]
  (h/html
    (h/head
      (map-stylesheets styles)
      (map-scripts scripts))
    (h/body (dissoc attr :styles :scripts) kids)))
