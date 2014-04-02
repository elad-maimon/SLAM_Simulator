# Robot SLAM Simulator [![endorse](https://api.coderwall.com/elad-maimon/endorsecount.png)](https://coderwall.com/elad-maimon)

SLAM (stands for Simultaneous Localization And Mapping) is a technique for robot's navigation in areas with no prior knowledge about it.

You can think of the algorithm that iRobots use to build a map of your house and know where the next place to clean.

This simulator implements the SLAM algorithm and simulate a "real" world conditions (like sensors read errors, false odometry etc...) and tries to build a map while navigating the area.

## Pre Requisits

Java 1.6 is required and SWT library that can run on your computer.
    
## Usage

1. Run the application
2. In the menu choose to draw a new map or use an existing one (map300.slm is a good example)
3. use the keys w,a,s,d to navigate the robot the map will start to built.

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
