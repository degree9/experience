(ns degree9.ui.height
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.height :as height]
            [uikit-hl.utility :as util]))

(h/defelem viewport [attr kids]
  (util/panel
    attr
    ::height/viewport {:expand true}
    kids))
