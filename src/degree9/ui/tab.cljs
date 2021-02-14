(ns degree9.ui.tab
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.margin :as margin]
            [uikit-hl.tab :as tab]))

(h/defelem tab [{:keys [items] :as attr} kids]
  (tab/tab
    (dissoc attr :items)
    (for [item items]
      (tab/item item))
    kids))
