TODO:

- fix near plane clipping issues. When example square is placed with z <= 36
    (which is ~ 25 * sqrt(2), the diagonal of the square), there are problems
    drawing the polygons of the square properly. The intended behavior is to
    "cut" the polygon at the near plane and truncate it. Look into this.
- create a distinction between pixel-based units and engine-based units. 
    Currently the sample square is defined in pixel units (50 wide, etc.), but
    is drawn using the same units. Instead it should be some arbitrary internal
    unit (say, 1 unit square) and drawn to a certain number of pixels based on
    camera settings. 