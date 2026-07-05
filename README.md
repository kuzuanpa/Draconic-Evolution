# Draconic-Evolution-TFRU

Fork of Draconic Evolution for TFRU.

## Fork focus
This fork emphasizes TFRU compatibility, visual upgrades, balance/config flexibility, and keeping the project buildable and publishable in the local environment.

## Notable changes
- Added TFRU environment detection and follow-up fixes around that integration.
- Integrated BrandonsCore and cleaned up surrounding project/build wiring.
- Ported newer reactor presentation work, including the 1.12+ style shader rendering and the "No Hope" reactor state.
- Fixed assorted bugs such as lore formatting issues, server crashes, and smaller rendering defects.
- Added or expanded many balance/configuration knobs for tools, weapons, bows, armor, capacitors, infusers, draconium blocks, the mob grinder, and the energy core.
- Performed refactors and dependency/build maintenance, including Gradle modernization, version sorting for Maven publication, and README/license updates.

## Build / publish notes
- Gradle build files and license files are present in the repository root.
- Recent history explicitly references buildability and Maven publication cleanup.
- This fork is documented here in terms of its TFRU-specific divergence rather than upstream gameplay details.
