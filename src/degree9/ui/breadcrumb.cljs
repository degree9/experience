(ns degree9.ui.breadcrumb
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.breadcrumb :as breadcrumb]))

(h/defelem item [{:keys [active] :as attr} kids]
  (breadcrumb/item
    (h/if-tpl active
      (h/span attr kids)
      (h/a attr kids))))

(h/defelem breadcrumb [attr kids]
  (breadcrumb/breadcrumb attr kids))
