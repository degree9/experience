(ns degree9.ui.subnav
  (:require [hoplon.core :as h]
            [uikit-hl.subnav :as subnav]
            [uikit-hl.utility :as util]))

(def subnav subnav/subnav)

(h/defelem item [{:keys [active] :as attr} kids]
  (subnav/item
    (dissoc attr :active)
    ::util/active active
    kids))
