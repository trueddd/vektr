## Vektr
Vektr is a tool for converting [Material icons](https://fonts.google.com/icons?icon.set=Material+Icons)
`.xml` files (Android Vector Drawable) into the code 
in a way Material icons are delivered with Jetpack Compose / Compose Multiplatform.
This tool basically parses icon XML file, parses path data of icon and splits it into
separate path elements and finally generates .kt file containing path drawing instructions.

<details>
  <summary>Example of generated file</summary>

```kotlin
package com.github.trueddd.ui.res.icons.map

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Suppress("UnusedReceiverParameter")
val Icons.Rounded.Map: ImageVector
    get() {
        if (savedMap != null) {
            return savedMap!!
        }
        savedMap = ImageVector.Builder(
            name = "Map",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        )
            .materialPath {
                moveTo(14.65f, 4.98f)
                lineToRelative(-5.0f, -1.75f)
                curveToRelative(-0.42f, -0.15f, -0.88f, -0.15f, -1.3f, -0.01f)
                lineTo(4.36f, 4.56f)
                curveTo(3.55f, 4.84f, 3.0f, 5.6f, 3.0f, 6.46f)
                verticalLineToRelative(11.85f)
                curveToRelative(0.0f, 1.41f, 1.41f, 2.37f, 2.72f, 1.86f)
                lineToRelative(2.93f, -1.14f)
                curveToRelative(0.22f, -0.09f, 0.47f, -0.09f, 0.69f, -0.01f)
                lineToRelative(5.0f, 1.75f)
                curveToRelative(0.42f, 0.15f, 0.88f, 0.15f, 1.3f, 0.01f)
                lineToRelative(3.99f, -1.34f)
                curveToRelative(0.81f, -0.27f, 1.36f, -1.04f, 1.36f, -1.9f)
                verticalLineTo(5.69f)
                curveToRelative(0.0f, -1.41f, -1.41f, -2.37f, -2.72f, -1.86f)
                lineToRelative(-2.93f, 1.14f)
                curveToRelative(-0.22f, 0.08f, -0.46f, 0.09f, -0.69f, 0.01f)
                close()
                moveTo(15.0f, 18.89f)
                lineToRelative(-6.0f, -2.11f)
                verticalLineTo(5.11f)
                lineToRelative(6.0f, 2.11f)
                verticalLineToRelative(11.67f)
                close()
            }
            .build()
        return savedMap!!
    }

private var savedMap: ImageVector? = null
```
</details>

There is a lot of Material icons that are not included into `androidx.compose.material.icons` package, 
and in order to use them you have to either import them as resource or parse path data in runtime. 
Both these approaches are valid, but I wanted new icons easy to modify and compile-time checked.

### CLI tool usage
Run downloaded or locally built jar file on the terminal:
```shell
java -jar vektr.jar <arguments>
```
Here is the list of available arguments:

| Name                  | Value          | Required | Example                        | Description                                                                                                                    |
|-----------------------|----------------|----------|--------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| Input file            | `in`           | true     | `--in=./search_24px.xml`       | Defines the source XML file                                                                                                    |
| Output directory      | `out`          | true     | `--out=./dir/`                 | Defines the directory where generated file will be placed                                                                      |
| Name of icon          | `n`, `name`    | false    | `--name=Search`                | The icon name, if not specified name of input file will be used                                                                |
| Output file name      | `f`, `file`    | false    | `--file=RoundedSearch`         | Name of file where generated code will be saved. If not specified, name of icon will be used                                   |
| Package name          | `p`, `package` | false    | `--package=com.github.trueddd` | Package name for the generated file                                                                                            |
| Icon style            | `s`, `style`   | false    | `--style=Outlined`             | Icon style that will be used as Icon receiver type. Must be one of these: `Outlined`, `Rounded`, `TwoTone`, `Sharp`, `Filled`. |
| Auto mirror           | `a`            | false    | `-a` (flag)                    | Generate this icon as auto mirrored                                                                                            |
| Use path data builder | `pd`           | false    | `-pd` (flag)                   | Use path data builder instead of materialPath function (not recommended)                                                       |

For example, sample code hidden in spoiler was generated with following command:
```shell
java -jar vektr.jar --name=Map --file=Rounded --package=com.github.trueddd.ui.res.icons.map --style=Rounded --in=./rounded_map_24px.xml --out=./
```

### Build locally
Run following command on terminal to build jar.
```shell
gradlew build
```
Optionally, you can create `local.properties` file in root folder and set path 
where you want jar to be placed after build:
```properties
target_path=<path>
```

### To-do
Here is planned tasks for this project, 
but do not hesitate to offer some changes or features via issues.
- [ ] Move code composing into common module
- [ ] Add web app for online file conversions
- [ ] Reduce jar size
- [ ] Use downloaded zip archives as conversion source (currently only XML files are supported)
- [ ] Use Material icon URL as conversion source
