(ns degree9.ui.card
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.card :as card]))

(def title card/title)

(h/defelem card [{:keys [header body footer] :as attr} kids]
  (card/card
    (dissoc attr :header :body :footer)
    (h/when-tpl header
      (card/header header))
    (h/if-tpl body
      (card/body body)
      kids)
    (h/when-tpl footer
      (card/footer footer))))
