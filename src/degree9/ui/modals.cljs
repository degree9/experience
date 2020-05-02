(ns degree9.ui.modals
  (:require [hoplon.core :as h]
            [uikit-hl.modal :as modal]))

(def ^:dynamic *id* nil)

(h/defelem modal [{:keys [id header] :as attr} kids]
  (bindings [id (or id *id* (gen-sym))]
    (modal/modal :id id
      (modal/dialog (dissoc attr :header :body :footer)
        (modal/header header)
        (modal/body body)
        (modal/footer footer)))))
