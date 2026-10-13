# ASM-DefUse

Domain: saltworks evaporation pans

Keys on: compiled .class files, analysed at the bytecode level for def-use pairs

Shape: no source, because this tool walks compiled method bodies for def-use pairs. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: It reads bytecode, not source. Nothing here is compiled and no .class file
  is shipped, so there is no method body for it to walk.

Expected: NOT TRIGGERED, no input discovered.
