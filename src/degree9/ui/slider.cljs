(ns degree9.ui.slider
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.core :as uk]
            [uikit-hl.slider :as slider]))

(defprotocol ISlider
  (show! [slider index] [slider index opts] "Shows a slider index."))

(extend-type js/Element
  ISlider
  (show!
    ([slider index] (show! slider index {}))
    ([slider index opts] (slider/show (uk/slider slider opts) index))))

(def slider slider/slider)

(def items slider/items)
