# campuscard-hce-plugin

> ## Creating plugins
> To create a new Capacitor v3 plugin, run `npm init @capacitor/plugin` <br>
>  
> https://capacitorjs.com/docs/v3/plugins/creating-plugins
>
> https://github.com/ionic-team/create-capacitor-plugin

# Installation

If you want to install it from a remote repository:

``` bash
# in the ionic app folder
npm install git+https://bb-git.zedat.fu-berlin.de/CampusCard/campuscard-hce-plugin.git
```

Alternatively, you can run npm install with your local copy of the plugin:

``` bash
# in the ionic app folder
npm install <path-to-theplugin-folder>
# for example
npm install ../campuscard-hce-plugin
```

# Configuration 

## AID for HCE-routing

The AID for HCE-routing has to be static and configured in [android/src/main/java/berlin/campuscard/hce/res/xml/hceservice.xml](). It is not possible to set in runtime even with native Android apps.

## Emulatig DESFire Applications

The Host Card Emulation is currently done entirely in the plugin (exclusively for Android, written in Java). It is not possible to use HCE in iOS at the moment. Emulating a DESFire-compatible "Secure Element" can be moved to JavaScript/TypeScript using [Capacitor event callbacks](https://capacitorjs.com/docs/v3/plugins/android#plugin-events), for example to be able to use dynamic values for the emulated DESFire Applications, such as:

* DESFire AID (e.g. *015548*)
* AES key (for example *00000000000000000000000000000000*)
* File Contents (for example *0035383536383600000048554853303538353638363000000000000000000000*).

It is then possible, ***but not secure at all***, to emulate multiple DESFire Applications concurently. 

For now, the plugin uses just one DESFire File (File No. 0) and just one Key (Key No. 0). Adding more Files and Keys is possible, but does not increase security. 
