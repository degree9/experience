(ns degree9.ui.navbar
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.navbar :as navbar]
            [uikit-hl.sticky :as sticky]))

(def left navbar/left)

(def center navbar/center)

(def right navbar/right)

(h/defelem navbar [{:keys [left center right sticky] :as attr} kids]
  (navbar/container
    ::sticky sticky
    (navbar/navbar
      (dissoc attr :left :center :right :sticky)
      (h/when-tpl left left)
      (h/when-tpl center center)
      (h/when-tpl right right))))
