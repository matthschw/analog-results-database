# analog-results-database
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) [![Java CI with Maven](https://github.com/electronics-and-drives/analog-results-database/actions/workflows/maven.yml/badge.svg)](https://github.com/electronics-and-drives/analog-results-database/actions/workflows/maven.yml)

Analog Results Database

This toolbox provides function for extracting simulation results
from Cadence Spectre and/or Ngspice and process the results.

## Dependencies

When the [GitHub Packages ](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry) 
are utilized, no manual actions must be performed.

When this functionality is not available, please
install the following dependencies manually:

- [nutmeg-reader](https://github.com/electronics-and-drives/nutmeg-reader) 

Clone the corresponding repositories, enter the directory and execute

```bash
$ mvn install
```
## Setup

### Java
Add the dependency to your project

```xml
<dependency>
  <groupId>edlab.eda</groupId>
  <artifactId>analog-results-database</artifactId>
  <version>0.0.1</version>
</dependency>
```

Import the corresponding package to your code
```java
import edlab.eda.ardb.*;
```

## API

### Java

The [JavaDoc](https://electronics-and-drives.github.io/analog-results-database/edlab/eda/ardb/package-summary.html)
is stored on the Github-Pages (branch *gh-pages*).

## License

Copyright (C) 2021, [Electronics & Drives](https://www.electronics-and-drives.de/)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see 
[https://www.gnu.org/licenses/](https://www.gnu.org/licenses).
