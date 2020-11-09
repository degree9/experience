(ns degree9.ui.navbar
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.navbar :as navbar]))

(h/defelem navbar [{:keys [left center right] :as attr} kids]
  (navbar/container
    (navbar/navbar attr
      (h/when-tpl left
        (navbar/left left))
      (h/when-tpl center
        (navbar/center center))
      (h/when-tpl right
        (navbar/right right)))))
