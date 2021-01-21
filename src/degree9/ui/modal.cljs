(ns degree9.ui.modal
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.core :as uk]
            [uikit-hl.close :as close]
            [uikit-hl.margin :as margin]
            [uikit-hl.modal :as modal]))

(defprotocol IModal
  (show! [modal] [modal opts] "Shows a modal.")
  (hide! [modal] [modal opts] "Hides a modal."))

(extend-type js/Element
  IModal
  (show!
    ([modal] (show! modal {}))
    ([modal opts] (modal/show (uk/modal modal opts))))
  (hide!
    ([modal] (hide! modal {}))
    ([modal opts] (modal/hide (uk/modal modal opts)))))


(h/defelem close [{:keys [large] :as attr :or {close {}}} _]
  (modal/close
    (dissoc attr :large :close)
    ::modal/close true
    ::close/close close
    ::close/large large))

(h/defelem header [{:keys [title] :as attr} kids]
  (modal/header
    (dissoc attr :title)
    (when title
      (modal/title title))
    kids))

(def title modal/title)

(def body modal/body)

(def footer modal/footer)

(h/defelem modal [{:keys [center container full] :as attr} kids]
  (modal/modal
    (dissoc attr :center :container :full)
    :container container :full full
    (modal/dialog ::margin/auto-vertical center
      kids)))
