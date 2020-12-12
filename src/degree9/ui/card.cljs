(ns degree9.ui.card
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.card :as card]))

(h/defelem card [{:keys [header body footer] :as attr} kids]
  (card/card
    (dissoc attr :header :body :footer)
    (when header header)
    (if body body kids)
    (when footer footer)))

(def header card/header)

(def title card/title)

(def body card/body)

(def footer card/footer)

(h/defelem media [{:keys [top bottom left right] :as attr} kids]
  (h/div
    (dissoc attr :top :bottom :left :right)
    ::card/media-top top
    ::card/media-bottom bottom
    ::card/media-left left
    ::card/media-right right
    kids))
