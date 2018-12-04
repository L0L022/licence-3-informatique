
trait Tree<T> {
    fn insert(&mut self, value: T);
    fn remove(&mut self, value: T);
    fn exist(&self, value: T) -> bool;
}

struct Node<T> {
    value: T,
    right: Option<Box<Node<T>>>,
    left: Option<Box<Node<T>>>,
}

impl<T: std::cmp::PartialEq + std::cmp::PartialOrd + Copy> Node<T> {
    fn search_node(&self, value: T) -> Option<&Node<T>> {
        if self.value == value {
            return Some(&self);
        }

        if let Some(ref n) = if value < self.value { &self.left } else { &self.right } {
            return n.search_node(value);
        }

        None
    }

    fn search_mut_node(&mut self, value: T) -> Option<&mut Node<T>> {
        if self.value == value {
            return Some(self);
        }

        if let Some(ref mut n) = if value < self.value { &mut self.left } else { &mut self.right } {
            return n.search_mut_node(value);
        }

        None
    }
}

impl<T: std::cmp::PartialEq + std::cmp::PartialOrd + Copy> Tree<T> for Node<T> {
    fn insert(&mut self, value: T) {
        
    }

    fn remove(&mut self, value: T) {

    }

    fn exist(&self, value: T) -> bool {
        self.search_node(value).is_some()
    }
}

#[cfg(test)]
mod tests {
    use ::*;
    #[test]
    fn exist() {
        let mut n = Node {
            value: 1,
            left: Some(Box::new(Node {
                value: 0,
                left: None,
                right: None,
            })),
            right: Some(Box::new(Node {
                value: 2,
                left: None,
                right: None,
            })),
        };

        assert_eq!(n.exist(0), true);
        assert_eq!(n.exist(1), true);
        assert_eq!(n.exist(2), true);

        if let Some(ref mut n) = n.search_mut_node(2) {
            n.value = 10;
        }

        assert_eq!(n.exist(2), false);
        assert_eq!(n.exist(10), true);
    }
}
