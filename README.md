A)	Setup instructions
   1) Prerequisites: Android Studio, latest kotlin version, Dagger Hilt lib, Kotlin compose, androidx paging compose,androidx navigation compose, coil, retrofit2:converter-gson, okhttp3:logging-interceptor,
   compose.material3, androidx-junit, androidx-espresso-core. Note- All the dependecies with latest versions only.
   
   2) Installation steps: 1.Clone the Repository:•
                         Open your terminal or command prompt.•
                         Use the git clone command followed by the repository URL. 
                         For example:Kotlingit clone https://github.com/your-username/your-repository-name.git•
                         Navigate into the cloned directory:cd your-repository-name
                        2.Open the Project in Android Studio:•
                         - Launch Android Studio.
                         - Select "New" from the welcome screen, or "File" > "Open" if a project is already open.
                        3.Install Dependencies (Gradle Sync):
                         - Add dependecies to the Gradle files (build.gradle.kts or build.gradle) and Sync it.
                         - go to "File" > "Sync Project with Gradle Files.
                         - This process will download all the declared dependencies in your project's build files.


   3) Configuration:
                     Java Development Kit (JDK):
                          •Android Studio usually comes with an embedded JDK. If you need a specific version,
                           go to "File" > "Settings" (or "Android Studio" > "Preferences" on macOS) > "Build, Execution, Deployment" > "Build Tools" > "Gradle".
                          •Under "Gradle JDK," you can select an appropriate JDK.
                      Android SDK:
                           • Ensure you have the required Android SDK Platform versions and Build Tools installed.•Go to "File" > "Settings" (or "Android Studio" > "Preferences" on macOS) > "Languages & Frameworks" > "Android SDK."
                           • In the "SDK Platforms" tab, check the Android API levels your project targets (usually specified in the module's build.gradle.kts file, e.g., compileSdk and targetSdk).
                           • In the "SDK Tools" tab, ensure "Android SDK Build-Tools," "Android Emulator," "Android SDK Platform-Tools," are installed and updated.
                     Build Variants:
                           • Select the appropriate build variant (e.g., debug or release) from the "Build Variants" tool window on the left side of Android Studio.
                     Local Properties: (local.properties):
                           •Some projects require API keys or other sensitive information to be stored in a local.properties file at the root of the project. This file is usually not version-controlled (i.e., listed in .gitignore).
                           • If the project requires it, create a local.properties file in the project's root directory.
                 
   4) Running the Application :
                        - Build and Run the Application:
                        - Once the Gradle sync is complete and the environment is configured, try building the project by selecting "Build" > "Make Project.
                        - To run the app, select a device (emulator or physical device) from the dropdown menu in the toolbar and click the "Run" button (green play icon).

 B)	Architecture overview:
   Technology Stack: Kotlin for programming language, Jetpack for UI, MVVM, Retrofit with OKHttp lib for api, Hilt DI, State flow for data stream.
   Data Flow: here user data will get from retrofit api and then using data class model will hold this and state flow emit this data to compose fun for render the list.
   Design Principles - Used MVVM design architecture
   
 C) Screenshots
![app screenshot 1](https://github.com/user-attachments/assets/c8cd7247-d313-4e3a-a50f-53bddf4ceff8)
![app screenshot 2](https://github.com/user-attachments/assets/8049f194-a847-40d5-8f7e-675768cb48b7)
![app screenshot 3](https://github.com/user-attachments/assets/a8d7fab7-af6d-4d79-abcf-4b6eeaa3f928)
