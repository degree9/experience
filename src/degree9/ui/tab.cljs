(ns degree9.ui.tab
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.margin :as margin]
            [uikit-hl.tab :as tab]))

(h/defelem tab [{:keys [items current] :as attr} kids]
  (tab/tab attr
    (h/for-tpl [item items]
      (j/cell-let [{:keys [label active disabled icon id]} item]
        (tab/item :click (partial reset! current id)
          ::tab/active (j/cell= active) ::tab/disabled (j/cell= disabled)
          (h/a
            (h/when-tpl icon
              (h/i ::margin/small-right true :class icon))
            (h/span ::margin/remove true (h/text "~{label}"))))))))
