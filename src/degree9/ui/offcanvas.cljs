(ns degree9.ui.offcanvas
  (:require [hoplon.core :as h]
            [uikit-hl.close :as close]
            [uikit-hl.offcanvas :as offcanvas]))

(def offcanvas offcanvas/offcanvas)

(def bar offcanvas/bar)

(h/defelem close [attr kids]
  (offcanvas/close
    attr
    ::close/close true
    kids))
