(ns degree9.ui.modal
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.button :as button]
            [uikit-hl.modal :as modal]))

(defn show! [elem & [opts]]
  (modal/show! (modal/modal! elem (clj->js (or opts {})))))

(h/defelem modal [{:keys [container full close outside header title body footer] :as attr} kids]
  (modal/modal :container container :full full
    (modal/dialog
      (h/when-tpl close
        (modal/close :outside outside :full full
          :default (j/cell= (not (or full outside)))))
      (h/when-tpl (j/cell= (or header title))
        (modal/header
          (h/if-tpl title (modal/title (h/text "~{title}")) header)))
      (h/if-tpl body
        (modal/body body)
        kids)
      (h/when-tpl footer
        (modal/footer footer)))))
