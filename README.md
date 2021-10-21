This is a Capacitor plugin supporting some specific NFC functionality which the
Campuscard project requires. At present, it does not do all that much. It only
works for Android (since a browser is obviously not an NFC-capable device, and
we can't use Apple's locked NFC API). It simulates a DESFire card with a single
application which in turn contains a single file. It supports authentication via
AES using a hardcoded key. For more, see "What it does" below. Here I'll just
note that it's not even close to dreaming of being production-ready.

There is a very simple companion app available in one of our other repositories.

## General information on Capacitor plugins
To create a new Capacitor v3 plugin, run `npm init @capacitor/plugin`
 
https://capacitorjs.com/docs/v3/plugins/creating-plugins

https://github.com/ionic-team/create-capacitor-plugin

# How to install

It's not on NPM, and it will be at least a little while before it is. You'll
need to clone the repository and install it locally:

``` bash
# in the ionic app folder
npm install <path-to-theplugin-folder>
```

# What it does

It emulates a Mifare DESFire card; that is to say, it informs Android that it would
like to handle all ISO 7816 "select by AID" commands for that card's AID. It
supports (and requires of the reader) AES authentication (only). The card which
it emulates contains a single application, which contains a single file, which
contains, at offset 10, several ASCII-encoded characters. (It really does assume
ASCII.  Sorry.) The characters look like 'HUHS012345$', where you can replace
the dollar sign with a digit by calling the plugin's `changeAppData` function.
(See `dist/docs.json`.)

It should work with any reader which begins its communication with cards by
sending an ISO 7816 "select by AID" command; it has been verified to work with
our little reader-driving Java application.

It uses the wrapped DESFire command set. It is generally impossible to emulate a
DESFire card and understand a reader using the native command set, since Android
will only route APDUs to applications if the first APDU in the communication is
an ISO 7816 "select by AID". It is possible to emulate a DESFire card and
understand a reader using ISO 7816 commands (which DESFire cards, of course,
also understand). However, this plugin does *not* do that. It would therefore be
difficult to modify it so it emulates a different card.

# How to configure and use

The plugin exposes all of two functions.

One is the aforementioned `changeAppData`; note that you *MUST* call this before
using the plugin at all (i. e., call it in some sort of initialization code),
since otherwise the data is (at present) uninitialized, and the app will crash
if you hold the phone to a reader.

The other function is not strictly a function; rather, the plugin emits
Capacitor plugin events, and you can listen to them and do whatever you like
with the information. The plugin event fires every time the plugin either sends
or receives an APDU, and the data it gives you contains a string representation
of that APDU. For example, the APDU 0x91, 0x5a, 0, 3, 0x11, 0x22, 0x11, 0 is
represented as "915A000311221100".

The Mifare DESFire AID is hardcoded, but then you're unlikely to want to change
that; the application ID, file ID and the key used for authentication are also
hardcoded. The data in the application is sort of hardcoded, as you saw above;
that is the easiest thing to change.
