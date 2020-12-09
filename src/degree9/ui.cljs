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

(defn responsive-viewport []
  (h/html-meta :name "viewport" :content "width=device-width, initial-scale=1.0"))

(h/defelem application [{:keys [styles scripts] :as attr} kids]
  (h/html
    (h/head
      (responsive-viewport)
      (map-stylesheets styles)
      (map-scripts scripts))
    (h/body (dissoc attr :styles :scripts) kids)))

(defn- html-coll [& args]
  (let [fragment (.createDocumentFragment js/document)]
    (.append fragment args)))
