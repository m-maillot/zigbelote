
import CoreImage

class ContentViewModel: ObservableObject, ObjectDetectorServiceLiveStreamDelegate {
    
  @Published var error: Error?
  @Published var frame: CGImage?

  var comicFilter = false
  var monoFilter = false
  var crystalFilter = false

  private let context = CIContext()

  private let cameraManager = CameraManager.shared
  private let frameManager = FrameManager.shared
  private var objectDetectorService: ObjectDetectorService? = nil

  init() {
    setupSubscriptions()
    objectDetectorService = ObjectDetectorService.liveStreamDetectorService(model: .efficientdetLite0, maxResults: 3, scoreThreshold: 0.5, liveStreamDelegate: self)
  }

  func setupSubscriptions() {
    
    // swiftlint:disable:next array_init
    cameraManager.$error
      .receive(on: RunLoop.main)
      .map { $0 }
      .assign(to: &$error)

    frameManager.$current
      .receive(on: RunLoop.main)
      .compactMap { buffer in
          let currentTimeMs = Date().timeIntervalSince1970 * 1000
          self.objectDetectorService?.detectAsync(sampleBuffer: buffer, orientation: .up, timeStamps: Int(currentTimeMs))
        guard let image = CGImage.create(from: buffer) else {
          return nil
        }

          let ciImage = CIImage(cgImage: image)

        return self.context.createCGImage(ciImage, from: ciImage.extent)
      }
      .assign(to: &$frame)
  }
    
    func objectDetectorService(_ objectDetectorService: ObjectDetectorService, didFinishDetection result: ResultBundle?, error: Error?) {
        guard let firstResult = result?.objectDetectorResults.first else {
            return
        }
        let data = firstResult?.detections.map({ detection in
            detection.categories.map { "\($0.categoryName) / \($0.score)"}
        })
        
        print("Detect result ! ->\(data)")
    }
    
}
