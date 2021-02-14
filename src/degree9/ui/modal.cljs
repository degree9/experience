(ns degree9.ui.modal
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.core :as uk]
            [uikit-hl.close :as close]
            [uikit-hl.margin :as margin]
            [uikit-hl.modal :as modal]))

(defprotocol IModal
  (modal! [modal] [modal opts] "Initialize an element as a modal.")
  (show!  [modal] [modal opts] "Shows a modal.")
  (hide!  [modal] [modal opts] "Hides a modal."))

(extend-type js/Element
  IModal
  (modal!
    ([modal] (modal! modal {}))
    ([modal opts] (uk/modal modal opts)))
  (show!
    ([modal] (show! modal {}))
    ([modal opts] (modal/show (modal! modal opts))))
  (hide!
    ([modal] (hide! modal {}))
    ([modal opts] (modal/hide (modal! modal opts)))))


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

(h/defelem modal [{:keys [center container full show hide] :as attr} kids]
  (modal/modal
    :container container :full full :show show :hide hide
    (modal/dialog
      (dissoc attr :center :container :full :show :hide)
      ::margin/auto-vertical center
      kids)))
