(ns degree9.ui.modals
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.modal :as modal]))

(defn show! [elem & [opts]]
  (modal/show! (modal/modal! elem (clj->js (or opts {})))))
