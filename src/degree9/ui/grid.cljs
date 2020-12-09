(ns degree9.ui.grid
  (:require [hoplon.core :as h]
            [uikit-hl.grid :as grid]
            [uikit-hl.height :as height]))

(h/defelem grid [attr kids]
  (grid/grid
    attr
    ::height/match {:row false}
    kids))
