(ns degree9.ui.text
  (:require [hoplon.core :as h]
            [uikit-hl.text :as text]))

(h/defelem h1 [{:keys [left center right] :as attr} kids]
  (h/h1
    (dissoc attr :left :center :right)
    ::text/left   left
    ::text/center center
    ::text/right  right
    kids))

(h/defelem h2 [{:keys [left center right] :as attr} kids]
  (h/h2
    (dissoc attr :left :center :right)
    ::text/left   left
    ::text/center center
    ::text/right  right
    kids))

(h/defelem h3 [{:keys [left center right] :as attr} kids]
  (h/h3
    (dissoc attr :left :center :right)
    ::text/left   left
    ::text/center center
    ::text/right  right
    kids))

(h/defelem h4 [{:keys [left center right] :as attr} kids]
  (h/h4
    (dissoc attr :left :center :right)
    ::text/left   left
    ::text/center center
    ::text/right  right
    kids))

(h/defelem h5 [{:keys [left center right] :as attr} kids]
  (h/h5
    (dissoc attr :left :center :right)
    ::text/left   left
    ::text/center center
    ::text/right  right
    kids))

(h/defelem h6 [{:keys [left center right] :as attr} kids]
  (h/h6
    (dissoc attr :left :center :right)
    ::text/left   left
    ::text/center center
    ::text/right  right
    kids))
