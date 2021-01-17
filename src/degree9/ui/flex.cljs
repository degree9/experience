(ns degree9.ui.flex
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.object :as obj]
            [uikit-hl.flex :as flex]))

(defmethod h/do! ::order
  [elem kw v]
  (obj/set-in elem [:style :order] v))

(defmulti order! h/kw-dispatcher :default ::default)

; (defmethod h/do! ::default
;   [elem kw v]
;   (h/do! elem ()))
