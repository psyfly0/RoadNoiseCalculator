# RoadNoiseCalculator
Road noise exposure calculation program according to Hungarian standards and regulations (based on the state of 2023).
The calculations and applicable regulations are based on the following:
- 314/2005. (XII. 25.) Korm. rendelet
- 284/2007. (X. 29.) Korm. rendelet
- 27/2008. (XII. 3.) KvVM-EüM együttes rendelet
- 93/2007. (XII. 18.) KvVM rendelet
- MSZ 18150-1: 1998 Hungarian Standard
- MSZ 15036: 2002 Hungarian Standard
- MSZ ISO 1996-1: 2020 Hungarian Standard
- MSZ ISO 1996-2: 2021 Hungarian Standard

The program reads a shapefile, calculates the equivalent A-weighted sound pressure level, the sound power level, the impact area, and the protective distance. 
It creates a buffer zone based on the last two parameters, which is then saved as a shapefile. It also saves a shapefile containing the computed results from the previous parameters.

The results can be directly used for the preparation of noise protection documentation or for the purpose of further noise modeling.
