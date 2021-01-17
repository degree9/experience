(ns degree9.ui.style
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.object :as obj]))

(defmethod h/do! ::default
  [elem kw val]
  (let [prop (name kw)
        style (obj/get elem :style)]
    (.setProperty style prop val)))
