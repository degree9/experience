(ns degree9.ui.placeholder
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.placeholder :as placeholder]))

(h/defelem placeholder [attr kids]
  (placeholder/placeholder attr kids))
