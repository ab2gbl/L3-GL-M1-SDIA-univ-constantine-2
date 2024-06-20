

map_australia = {
    'WA': ['NT', 'SA'],
    'NT': ['SA', 'WA', 'Q'],
    'Q': ['NT', 'NSW', 'SA'],
    'SA': ['WA', 'NT', 'Q', 'NSW', 'V'],
    'NSW': ['V', 'Q', 'SA'],
    'V': ['NSW', 'SA'],
    'T': []
}

colors = ['blue', 'vert', 'rouge']


def is_valid_assignment(csp, the_map):
    for region, adjacent_regions in the_map.items():
        if region in csp:
            color = csp[region]
            for adjacent_region in adjacent_regions:
                if adjacent_region in csp and csp[adjacent_region] == color:
                    return False
    return True


def coloring(csp, the_map, colors):
    if len(csp) == len(the_map):
        return csp

    for key in the_map.keys():
        if key not in csp:
            element = key
            break

    for color in colors:
        csp[element] = color

        if is_valid_assignment(csp, the_map):
            result = coloring(csp, the_map, colors)
            if result != "echec":
                return result

        del csp[element]

    return "echec"


result = coloring({}, map_australia, colors)
if result != "echec":
    print(result)
else:
    print("No valid coloring found.")

                      
        

         
        
        
    
    

