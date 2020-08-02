(ns degree9.ui.placeholders
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.flex :as flex]
            [uikit-hl.height :as height]
            [uikit-hl.margin :as margin]
            [uikit-hl.placeholder :as placeholder]
            [uikit-hl.width :as width]))

(h/defelem placeholder [{:keys [title subtitle icon] :as attr} kids]
  (placeholder/placeholder attr
    ::width/width-1-1 true
    ::height/viewport "expand: true"
    ::flex/flex true ::flex/center true ::flex/middle true ::flex/column true
    (h/when-tpl icon (h/i :class [:fal icon :fa-5x]))
    (h/h1 ::margin/remove-bottom true title)
    (h/h3 subtitle)))
